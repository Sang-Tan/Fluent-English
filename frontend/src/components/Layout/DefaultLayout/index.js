import { useState } from "react";

import { Fragment } from "react";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

import styles from "./DefaultLayout.module.scss";
import classNames from "classnames/bind";

const cx = classNames.bind(styles);

function DefaultLayout({ children }) {
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
      <div className={cx("main", { "sidebar-open": sidebarOpen })}>
        {children}
      </div>
    </Fragment>
  );
}

export default DefaultLayout;
