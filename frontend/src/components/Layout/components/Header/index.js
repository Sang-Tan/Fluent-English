import { forwardRef } from "react";

import classNames from "classnames/bind";
import styles from "./Header.module.scss";

import { List as ListIcon } from "react-bootstrap-icons";
import { Dropdown } from "react-bootstrap";

const cx = classNames.bind(styles);

const NavItem = forwardRef(({ children, onClick }, ref) => {
  return (
    <li href="" ref={ref} className={cx("nav-item", "pe-3")} onClick={onClick}>
      {children}
    </li>
  );
});

function Header({ toggleSidebar }) {
  return (
    <header className={cx("header", "fixed-top d-flex align-items-center")}>
      <div className={cx("d-flex align-items-center justify-content-between")}>
        <a href="/" className={cx("logo", "d-flex align-item-center")}>
          <img src="/images/logo.png" alt="logo" />
          <span className={cx("d-none d-lg-block")}>Fluent English</span>
        </a>
        <ListIcon
          className={cx("toggle-sidebar-btn")}
          onClick={toggleSidebar}
        />
      </div>
      <nav className={cx("header-nav", "ms-auto")}>
        <ul>
          <Dropdown>
            <Dropdown.Toggle as={NavItem} id="dropdown-custom-components">
              <div
                href="#"
                className={cx(
                  "nav-link",
                  "nav-profile",
                  "d-flex align-items-center pe-0"
                )}
              >
                <img
                  src="/images/profile-img.jpg"
                  alt="avatar"
                  className={cx("rounded-circle")}
                />
              </div>
            </Dropdown.Toggle>

            <Dropdown.Menu>
              <Dropdown.Item href="#/action-1">Profile</Dropdown.Item>
              <Dropdown.Item href="#/action-2">Setting</Dropdown.Item>
              <Dropdown.Item href="#/action-3">Logout</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </ul>
      </nav>
    </header>
  );
}

export default Header;
