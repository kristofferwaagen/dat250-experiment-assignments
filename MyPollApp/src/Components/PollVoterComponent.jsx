import React, { useState } from "react";

function PollVoterComponent({ poll }) {
  // Local state to manage live updates of vote counts
  const [voteOptions, setVoteOptions] = useState(poll.voteOptions);

  const upvote = async (index) => {
    try {
      const response = await fetch(`polls/${poll.id}/options/${index}/upvote`, {
        method: "POST",
      });
      if (!response.ok) {
        console.error("Failed to upvote");
      } else {
        console.log("Upvoted successfully");

        // Update the local state to reflect the upvote
        const updatedOptions = [...voteOptions];
        updatedOptions[index].votes += 1;
        setVoteOptions(updatedOptions);
      }
    } catch (error) {
      console.error("Error during upvote:", error);
    }
  };

  const downvote = async (index) => {
    try {
      const response = await fetch(
        `polls/${poll.id}/options/${index}/downvote`,
        {
          method: "POST",
        }
      );
      if (!response.ok) {
        console.error("Failed to downvote");
      } else {
        console.log("Downvoted successfully");

        // Update the local state to reflect the downvote
        const updatedOptions = [...voteOptions];
        updatedOptions[index].votes -= 1;
        setVoteOptions(updatedOptions);
      }
    } catch (error) {
      console.error("Error during downvote:", error);
    }
  };

  return (
    <div>
      <h2>{poll.question}</h2>
      {voteOptions.map((option, index) => (
        <div key={index}>
          <span>{option.caption}</span>
          <button type="button" onClick={() => upvote(index)}>
            upvote
          </button>
          <button type="button" onClick={() => downvote(index)}>
            downvote
          </button>
          <span>{option.votes} Votes</span>
        </div>
      ))}
    </div>
  );
}

export default PollVoterComponent;
