import DefaultLayout from "./components/Layout/DefaultLayout";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import { publicRoutes } from "./routes";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {publicRoutes.map((route, index) => (
          <Route key={index} path={route.path} element={<DefaultLayout />} />
        ))}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
