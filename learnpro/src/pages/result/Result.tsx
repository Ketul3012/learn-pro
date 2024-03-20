import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

export const Result = () => {
  const [learningPath, setLearningPath] = useState("");
  const { state } = useLocation();
  const navigate = useNavigate();

  return (
    <div className='h-full flex flex-col items-start'>
      <h1 className='text-center text-2xl my-4 px-2 font-semibold'>
        Hello, {state?.email}
      </h1>
      <h1 className='text-center text-2xl my-4'>
        Enter your details and System will give you an AI based Day-Wise
        learning path to enhance you skills. System will also send an email with
        your learning path.
      </h1>
      <div className='flex flex-col lg:flex-row w-full lg:gap-4 px-4'>
        <input
          placeholder='Things you want to learn....'
          value={learningPath}
          inputMode='text'
          onChange={(e) => {
            setLearningPath(e.target.value);
          }}
          className='p-4 rounded-full border-[2px] border-gray-500 my-3 flex-1'
        />
        <button
          className='p-4 px-20 rounded-full self-center border-[2px] bg-blue-400 hover:bg-blue-500 hover:scale-[1.05] transition-transform shadow-lg text-white my-3'
          onClick={() => {}}
        >
          Generate a Path
        </button>
      </div>
      <div className='flex flex-col mt-3 px-6 w-full'>
        <h1 className='text-xl font-semibold'>Your existing learning paths</h1>
        <div className='grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-10 my-3'>
          {new Array(20).fill(0).map((_, index) => (
            <div
              className='w-full aspect-square flex flex-row items-center justify-center bg-red-100 cursor-pointer hover:scale-[1.05] transition-transform rounded-lg shadow-lg'
              key={index}
              onClick={() => {
                navigate("/path/" + index);
              }}
            >
              <h6>Path name {index}</h6>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
