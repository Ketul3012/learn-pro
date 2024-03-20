import { HashRouter as Router, Routes, Route } from "react-router-dom";
import { Home } from "./pages/home/Home";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Result } from "./pages/result/Result";
import { Path } from "./pages/path/Path";

function App() {
  return (
    <div className='h-full w-full flex flex-col'>
      <Router>
        <div className='flex-1'>
          <Routes>
            <Route path='/' Component={Home} />
            <Route path='/result' Component={Result} />
            <Route path='/path/:id' Component={Path} />
          </Routes>
        </div>
      </Router>
      <ToastContainer position='top-right' />
    </div>
  );
}

export default App;
