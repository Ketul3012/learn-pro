import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { RoadMap, RoadMapItem } from "../../model/RoadMapResponse";
import { postRequest } from "../../utils/network-manager";
import { toast } from "react-toastify";

export const Path = () => {
  const params = useParams();
  const [roadMap, setRoadMap] = useState<RoadMap | undefined>(undefined);
  useEffect(() => {
    const _init = async () => {
      toast.promise(
        postRequest<{ message: string; roadMap: RoadMap }>("", {
          type: "getById",
          id: params.id,
          message: "",
          email: "",
        }),
        {
          pending: "Fetching your learning path",
          error: "No data found",
          success: {
            render({ data }) {
              setRoadMap(data.data.roadMap);
              return data.data.message;
            },
          },
        }
      );
    };

    if (params.id) {
      _init();
    }
  }, [params]);

  const _renderDayDetails = (item: RoadMapItem, index: number) => {
    return (
      <div
        key={index}
        className='flex flex-col w-full bg-slate-100 p-4 rounded-sm shadow-md my-2'
      >
        <h1 className='font-semibold text-lg'>{item.title}</h1>
        <hr className='my-2 !text-black !bg-black' />

        <h1>{"Tasks for the day"}</h1>
        <hr className='!text-black !bg-black my-1' />
        {item.tasks.map((item, index) => (
          <p key={index}>{item}</p>
        ))}
        <h1 className='mt-2'>{"Resources"}</h1>
        <hr className='!text-black !bg-black my-1' />
        {item.resources.map((item, index) => {
          const itemParts = item.split(": ");

          if (itemParts.length === 2) {
            return (
              <>
                <p key={index}>
                  {itemParts[0]} :{" "}
                  <span className='underline'>{itemParts[1].trim()}</span>
                </p>
              </>
            );
          } else {
            return <p key={index}>{item}</p>;
          }
        })}
      </div>
    );
  };

  return (
    <div className='px-4 pt-4'>
      <h1 className='text-2xl font-semibold'>
        Path: {roadMap?.prompt || "No Name"}
      </h1>
      <div className='flex flex-col'>
        {roadMap?.roadMapItems.map((item, index) =>
          _renderDayDetails(item, index)
        )}
      </div>
    </div>
  );
};
