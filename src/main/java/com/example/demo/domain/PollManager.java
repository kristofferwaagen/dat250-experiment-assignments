package com.example.demo.domain;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PollManager {
    private Map<Integer, Poll> polls = new HashMap<>();
    private Map<Poll, Map<User, Vote>> votes = new HashMap<>();
    private int nextPollId = 1; // To generate unique poll IDs

    private Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void addPoll(Poll poll) {
        poll.setId(nextPollId++); // Set unique ID
        polls.put((int) poll.getId(), poll);
        votes.put(poll, new HashMap<>());
    }

    public Poll getPollById(int id) {
        return polls.get(id);
    }

    public Map<Integer, Poll> getPolls() {
        return polls;
    }

    public List<Vote> getVotesForPoll(Poll poll) {
        if (votes.containsKey(poll)) {
            return new ArrayList<>(votes.get(poll).values());
        }
        return Collections.emptyList();
    }

    public void deletePoll(int id) {
        Poll poll = polls.remove(id);
        if (poll != null) {
            votes.remove(poll);
        }
    }

    public void addVote(User user, Poll poll, Vote vote) {
        Map<User, Vote> pollVotes = votes.get(poll);
        if (pollVotes != null) {
            pollVotes.put(user, vote);
        }
    }

    public void clearPolls() {
        polls.clear();
        votes.clear();
    }

    public void clearUsers() {
        users.clear();
    }
}
