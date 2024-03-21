import { useCallback, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { postRequest } from "../../utils/network-manager";
import { RoadMapResponse } from "../../model/RoadMapResponse";
import { toast } from "react-toastify";

export const Result = () => {
  const [learningPath, setLearningPath] = useState("");
  const { state } = useLocation();
  const navigate = useNavigate();

  const [roadMapResponse, setRoadMapResponse] = useState<
    RoadMapResponse | undefined
  >(undefined);

  const _fetchResults = useCallback(async () => {
    if (state?.email) {
      toast.promise(
        postRequest<RoadMapResponse>("", {
          type: "get",
          email: state.email,
          message: "",
        }),
        {
          pending: "Fetching Your learning paths",
          error: "Not able to fetch your learning paths",
          success: {
            render({ data }) {
              setRoadMapResponse(data.data);
              return data.data.message;
            },
          },
        }
      );
    }
  }, [state.email]);

  useEffect(() => {
    _fetchResults();
  }, [_fetchResults, state]);

  const _generateRoadMap = async () => {
    toast.promise(
      postRequest<{ message: string }>("", {
        type: "post",
        email: state.email,
        message: learningPath,
      }),
      {
        pending: "Generating a roadmap for you",
        error: "Error generating roadmap",
        success: {
          render({ data }) {
            _fetchResults();
            return data.data.message;
          },
        },
      }
    );
  };

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
          onClick={async () => {
            await _generateRoadMap();
          }}
        >
          Generate a Path
        </button>
      </div>
      <div className='flex flex-col mt-3 px-6 w-full'>
        <h1 className='text-xl font-semibold'>Your existing learning paths</h1>
        <div className='grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-10 my-3'>
          {roadMapResponse?.roadMaps?.map((item, index) => (
            <div
              className='w-full aspect-square flex flex-row items-center justify-center bg-red-100 cursor-pointer hover:scale-[1.05] transition-transform rounded-lg shadow-lg'
              key={index}
              onClick={() => {
                navigate("/path/" + item.id);
              }}
            >
              <h6>Path: {item.prompt || "No Name"}</h6>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
