package com.example.demo.controllers;

import com.example.demo.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollManager pollManager;

    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    // 1. Create a new poll
    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        pollManager.addPoll(poll);
        return ResponseEntity.status(HttpStatus.CREATED).body(poll);  // Return the created poll with 201 status
    }

    // 2. List all polls
    @GetMapping
    public List<Poll> listAllPolls() {
        return new ArrayList<>(pollManager.getPolls().values());
    }

    // 3. User votes on a poll
    @PostMapping("/{pollId}/vote")
    public ResponseEntity<String> voteOnPoll(@PathVariable int pollId, @RequestBody Map<String, String> voteDetails) {
        String username = voteDetails.get("username");
        String voteOptionCaption = voteDetails.get("voteOption");

        User user = pollManager.getUser(username);
        Poll poll = pollManager.getPollById(pollId);

        if (user == null || poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user or poll");
        }

        VoteOption selectedOption = poll.getVoteOptions().stream()
            .filter(option -> option.getCaption().equals(voteOptionCaption))
            .findFirst()
            .orElse(null);

        if (selectedOption == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid vote option");
        }

        Vote vote = new Vote();
        vote.setPublishedAt(Instant.now());
        vote.setVoteOption(selectedOption);

        pollManager.addVote(user, poll, vote);

        return ResponseEntity.status(HttpStatus.OK).body("Vote recorded");
    }

    // 4. List votes for a poll
    @GetMapping("/{pollId}/votes")
    public ResponseEntity<List<Vote>> listVotes(@PathVariable int pollId) {
        Poll poll = pollManager.getPollById(pollId);
        if (poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(pollManager.getVotesForPoll(poll));
    }

    // 5. Delete a poll
    @DeleteMapping("/{pollId}")
    public ResponseEntity<String> deletePoll(@PathVariable int pollId) {
        Poll poll = pollManager.getPollById(pollId);
        if (poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }
        pollManager.deletePoll(pollId);
        return ResponseEntity.status(HttpStatus.OK).body("Poll deleted");
    }

    // 6. Upvote an option
    @PostMapping("/{pollId}/options/{optionIndex}/upvote")
    public ResponseEntity<String> upvote(@PathVariable int pollId, @PathVariable int optionIndex) {
        Poll poll = pollManager.getPollById(pollId);
        if (poll == null || optionIndex < 0 || optionIndex >= poll.getVoteOptions().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll or option not found");
        }

        VoteOption option = poll.getVoteOptions().get(optionIndex);
        option.setVotes(option.getVotes() + 1);  // Increment vote count
        return ResponseEntity.status(HttpStatus.OK).body("Upvoted successfully");
    }

    // 7. Downvote an option
    @PostMapping("/{pollId}/options/{optionIndex}/downvote")
    public ResponseEntity<String> downvote(@PathVariable int pollId, @PathVariable int optionIndex) {
        Poll poll = pollManager.getPollById(pollId);
        if (poll == null || optionIndex < 0 || optionIndex >= poll.getVoteOptions().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll or option not found");
        }

        VoteOption option = poll.getVoteOptions().get(optionIndex);
        option.setVotes(option.getVotes() - 1);  // Decrement vote count
        return ResponseEntity.status(HttpStatus.OK).body("Downvoted successfully");
    }

    // 8. Clear all polls
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllPolls() {
        pollManager.clearPolls();
        return ResponseEntity.status(HttpStatus.OK).body("All polls cleared");
    }
}
