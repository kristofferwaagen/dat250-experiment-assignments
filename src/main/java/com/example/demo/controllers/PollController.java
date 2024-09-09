package com.example.demo.controllers;

import com.example.demo.domain.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollManager pollManager;

    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    // 1. Create a new poll
    @PostMapping
    public String createPoll(@RequestBody Poll poll) {
        pollManager.addPoll(poll);
        return "Poll created";
    }

    // 2. List all polls
    @GetMapping
    public List<Poll> listAllPolls() {
        // Fix: Convert Map<String, Poll> to List<Poll>
        return new ArrayList<>(pollManager.getPolls().values());
    }

    // 3. User votes on a poll
    @PostMapping("/{question}/vote")
    public String voteOnPoll(@PathVariable String question, @RequestBody Map<String, String> voteDetails) {
        String username = voteDetails.get("username");
        String voteOptionCaption = voteDetails.get("voteOption");

        User user = pollManager.getUser(username);
        Poll poll = pollManager.getPoll(question);

        if (user == null || poll == null) {
            return "Invalid user or poll";
        }

        VoteOption selectedOption = poll.getVoteOptions().stream()
            .filter(option -> option.getCaption().equals(voteOptionCaption))
            .findFirst()
            .orElse(null);

        if (selectedOption == null) {
            return "Invalid vote option";
        }

        // Fix: Use Instant.now() for setting published time
        Vote vote = new Vote();
        vote.setPublishedAt(Instant.now());
        vote.setVoteOption(selectedOption);
        
        pollManager.addVote(user, poll, vote);

        return "Vote recorded";
    }

    // 4. List votes for a poll
    @GetMapping("/{question}/votes")
    public List<Vote> listVotes(@PathVariable String question) {
        Poll poll = pollManager.getPoll(question);
        return pollManager.getVotesForPoll(poll);
    }

    // 5. Delete a poll
    @DeleteMapping("/{question}")
    public String deletePoll(@PathVariable String question) {
        pollManager.deletePoll(question);
        return "Poll deleted";
    }
}
