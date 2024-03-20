import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

export const Home = () => {
  const [email, setEmail] = useState("");
  const navigate = useNavigate();
  return (
    <div className='h-full flex flex-col items-center justify-center'>
      <div className='flex flex-col p-10 bg-slate-100 rounded-lg z-10 shadow-lg w-full md:w-[40%]'>
        <h1 className='text-center text-2xl'>Login with your email address</h1>
        <input
          placeholder='Email address'
          value={email}
          type='email'
          inputMode='text'
          onChange={(e) => {
            setEmail(e.target.value);
          }}
          className='p-4 rounded-full border-[2px] border-gray-500 my-3 w-full'
        />
        <button
          className='p-4 px-20 rounded-full w-[50%] self-center bg-blue-400 hover:bg-blue-500 shadow-lg hover:scale-[1.05] transition-transform text-white my-3'
          onClick={() => {
            if (!emailRegex.test(email)) {
              toast("Email needs to be in proper format", {
                type: "error",
              });
              return;
            }

            navigate("/result", {
              state: { email: email },
            });
          }}
        >
          Submit
        </button>
      </div>
    </div>
  );
};
