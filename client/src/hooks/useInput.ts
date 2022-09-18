import React, { useState } from "react";

const useInput = (defaultValue: string) => {
  const [value, setValue] = useState(defaultValue);
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    //const value=e.target.value;
    const {
      target: { value },
    } = e;
    setValue(value);
  };

  return { value, onChange };
};

export default useInput;
