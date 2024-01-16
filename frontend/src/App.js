import DefaultLayout from "./components/Layout/DefaultLayout";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import { publicRoutes, privateRoutes } from "./routes";
import { NotFound } from "./pages/error";
import { AuthContextProvider } from "./contexts/AuthContext";
import AuthPath from "./components/AuthPath";
import { Fragment } from "react";

import "react-toastify/dist/ReactToastify.css";

function AppRoutes() {
  let i = 0;
  return (
    <Routes>
      {publicRoutes.map((route) => {
        return getAppRoute({ route, isPrivate: false, index: i++ });
      })}
      {privateRoutes.map((route) => {
        return getAppRoute({ route, isPrivate: true, index: i++ });
      })}
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

function getAppRoute({ route, isPrivate, index }) {
  const PageComponent = route.page || NotFound;

  let LayoutComponent = DefaultLayout;
  if (route.layout === null) {
    LayoutComponent = Fragment;
  } else if (route.layout) {
    LayoutComponent = route.layout;
  }

  return (
    <Route
      key={index}
      path={route.path}
      element={
        isPrivate ? (
          <AuthPath>
            <LayoutComponent>
              <PageComponent />
              <ToastContainer />
            </LayoutComponent>
          </AuthPath>
        ) : (
          <LayoutComponent>
            <PageComponent />
            <ToastContainer />
          </LayoutComponent>
        )
      }
    />
  );
}

function App() {
  return (
    <AuthContextProvider>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </AuthContextProvider>
  );
}

export default App;
