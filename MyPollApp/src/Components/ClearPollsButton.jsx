import React, { useState } from "react";

function ClearPollsButton() {
  const [statusMessage, setStatusMessage] = useState("");

  const clearPolls = async () => {
    try {
      const response = await fetch("/polls/clear", {
        method: "DELETE",
      });
      if (response.ok) {
        setStatusMessage("All polls cleared successfully.");
      } else {
        setStatusMessage("Failed to clear polls.");
      }
    } catch (error) {
      setStatusMessage("Error occurred while clearing polls.");
    }
  };

  return (
    <div>
      <button onClick={clearPolls}>Clear All Polls</button>
      {statusMessage && <p>{statusMessage}</p>}
    </div>
  );
}

export default ClearPollsButton;
