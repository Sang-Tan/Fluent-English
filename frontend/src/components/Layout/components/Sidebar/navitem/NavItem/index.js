import styles from "./../styles.module.scss";
import classnames from "classnames/bind";

import { NavLink } from "react-router-dom";

const cx = classnames.bind(styles);

function NavItem({ href, title, icon: Icon }) {
  return (
    <li className={cx("nav-item")}>
      <NavLink
        className={({ isActive }) => cx("nav-link", { active: isActive })}
        to={href}
      >
        <Icon />
        <span>{title}</span>
      </NavLink>
    </li>
  );
}

export default NavItem;
