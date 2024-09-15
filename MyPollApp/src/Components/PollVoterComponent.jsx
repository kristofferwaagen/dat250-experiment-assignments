import React, { useState } from "react";

function PollVoterComponent({ poll }) {
  const [errorMessage, setErrorMessage] = useState("");

  const upvote = async (index) => {
    poll.options[index].votes += 1;
    const response = await fetch(`/polls/${poll.id}/options/${index}/upvote`, {
      method: "POST",
    });

    if (!response.ok) {
      console.error("Failed to upvote");
      poll.options[index].votes -= 1; // Revert the change if the upvote fails
      setErrorMessage("Failed to upvote");
    } else {
      console.log("Upvoted successfully");
      setErrorMessage(""); // Clear any previous error messages
    }
  };

  const downvote = async (index) => {
    poll.options[index].votes -= 1;
    const response = await fetch(
      `/polls/${poll.id}/options/${index}/downvote`,
      {
        method: "POST",
      }
    );

    if (!response.ok) {
      console.error("Failed to downvote");
      poll.options[index].votes += 1; // Revert the change if the downvote fails
      setErrorMessage("Failed to downvote");
    } else {
      console.log("Downvoted successfully");
      setErrorMessage(""); // Clear any previous error messages
    }
  };

  return (
    <div>
      <h2>{poll.question}</h2>
      {poll.options.map((option, index) => (
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
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
    </div>
  );
}

export default PollVoterComponent;
