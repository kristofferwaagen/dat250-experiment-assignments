package com.example.demo.domain;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PollManager {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Poll> polls = new HashMap<>();
    private Map<Poll, Map<User, Vote>> votes = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addPoll(Poll poll) {
        polls.put(poll.getQuestion(), poll);
        votes.put(poll, new HashMap<>());
    }

    public Poll getPoll(String question) {
        return polls.get(question);
    }

    public Map<String, Poll> getPolls() {
        return polls;
    }

    public void addVote(User user, Poll poll, Vote vote) {
        Map<User, Vote> pollVotes = votes.get(poll);
        if (pollVotes != null) {
            pollVotes.put(user, vote);
        }
    }

    public List<Vote> getVotesForPoll(Poll poll) {
        if (votes.containsKey(poll)) {
            return new ArrayList<>(votes.get(poll).values());
        }
        return Collections.emptyList();
    }

    public void deletePoll(String question) {
        Poll poll = polls.remove(question);
        if (poll != null) {
            votes.remove(poll);
        }
    }
}
