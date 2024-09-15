import React, { useState } from "react";

function CounterComponent() {
  const [count, setCount] = useState(0);

  const increment = () => {
    setCount(count + 1);
  };

  return <button onClick={increment}>count is {count}</button>;
}

export default CounterComponent;
