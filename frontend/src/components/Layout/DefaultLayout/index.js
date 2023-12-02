import { Fragment } from "react";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

function DefaultLayout() {
  return (
    <Fragment>
      <Header />
      <Sidebar />
    </Fragment>
  );
}

export default DefaultLayout;
