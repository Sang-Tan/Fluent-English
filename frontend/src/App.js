import DefaultLayout from "./components/Layout/DefaultLayout";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import { publicRoutes } from "./routes";
import { NotFound } from "./pages/error";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {publicRoutes.map((route, index) => {
          const PageComponent = route.page || NotFound;
          return (
            <Route
              key={index}
              path={route.path}
              element={
                <DefaultLayout>
                  <PageComponent />
                </DefaultLayout>
              }
            />
          );
        })}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
