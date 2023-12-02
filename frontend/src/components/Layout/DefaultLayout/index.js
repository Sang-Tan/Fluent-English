import { useState } from "react";

import { Fragment } from "react";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

function DefaultLayout() {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const openSidebar = () => setSidebarOpen(true);
  const closeSidebar = () => setSidebarOpen(false);

  const toggleSidebar = () => {
    if (sidebarOpen) {
      closeSidebar();
    } else {
      openSidebar();
    }
  };

  return (
    <Fragment>
      <Header toggleSidebar={toggleSidebar} />
      <Sidebar show={sidebarOpen} />
    </Fragment>
  );
}

export default DefaultLayout;
