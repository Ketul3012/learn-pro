import { useParams } from "react-router-dom";

export const Path = () => {
  const params = useParams();

  const _renderDayDetails = (item: unknown, index: number) => {
    return (
      <div
        key={index}
        className='flex flex-col w-full md:w-[60%] bg-slate-100 p-4 rounded-sm shadow-md my-2'
      >
        <h1>Day {index}</h1>
        <hr className='my-2 !text-black !bg-black' />
        <h1>Title</h1>
        <h2>Description</h2>
        <h2 className='cursor-pointer'>link</h2>
      </div>
    );
  };

  return (
    <div className='px-4 pt-4'>
      <h1 className='text-2xl font-semibold'>Path {params?.id}</h1>
      <div className='flex flex-col'>
        {new Array(10)
          .fill(0)
          .map((item, index) => _renderDayDetails(item, index))}
      </div>
    </div>
  );
};
