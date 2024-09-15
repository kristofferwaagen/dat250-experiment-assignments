import React, { useState } from "react";
import PollVoterComponent from "./PollVoterComponent";

function PollCreationComponent() {
  const [question, setQuestion] = useState("");
  const [options, setOptions] = useState([""]);
  const [createdPoll, setCreatedPoll] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");

  const addOptions = () => {
    setOptions([...options, ""]);
  };

  const removeOptions = (index) => {
    setOptions(options.filter((_, i) => i !== index));
  };

  const createPoll = async () => {
    const poll = {
      question,
      publishedAt: new Date().toISOString(),
      validUntil: new Date(
        new Date().getTime() + 24 * 60 * 60 * 1000
      ).toISOString(),
      options: options.map((option, index) => ({
        caption: option,
        presentationOrder: index + 1,
        votes: 0,
      })),
    };

    try {
      const response = await fetch("/polls", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(poll),
      });

      if (response.ok) {
        const createdPollData = await response.json();
        setCreatedPoll(createdPollData);
        setErrorMessage(""); // Clear any previous error messages
        console.log("Poll created successfully:", createdPollData);
      } else {
        const errorData = await response.text();
        console.error("Failed to create poll:", errorData);
        setErrorMessage(`Error: ${errorData}`);
      }
    } catch (error) {
      console.error("Network or server error:", error);
      setErrorMessage("Network or server error");
    }
  };

  return (
    <div>
      <h2>Create a New Poll</h2>
      <input
        type="text"
        value={question}
        onChange={(e) => setQuestion(e.target.value)}
        placeholder="Enter your question"
      />
      {options.map((option, index) => (
        <div key={index}>
          <input
            type="text"
            value={options[index]}
            onChange={(e) => {
              const newOptions = [...options];
              newOptions[index] = e.target.value;
              setOptions(newOptions);
            }}
            placeholder={`Option ${index + 1}`}
          />
          <button type="button" onClick={() => removeOptions(index)}>
            Remove
          </button>
        </div>
      ))}
      <button type="button" onClick={addOptions}>
        Add Option
      </button>
      <button type="button" onClick={createPoll}>
        Create Poll
      </button>

      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}

      {createdPoll && <PollVoterComponent poll={createdPoll} />}
    </div>
  );
}

export default PollCreationComponent;
