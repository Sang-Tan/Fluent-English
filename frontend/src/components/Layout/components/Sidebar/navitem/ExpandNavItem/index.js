import styles from "./../styles.module.scss";
import classnames from "classnames/bind";

import { useState } from "react";
import { Circle } from "react-bootstrap-icons";
import { Collapse } from "react-bootstrap";
import NavItem from "../NavItem";

const cx = classnames.bind(styles);

function ChildItem({ href, title }) {
  return <NavItem href={href} title={title} icon={Circle} />;
}

function ExpandNavItem({ title, icon: Icon, childItems }) {
  const [open, setOpen] = useState(false);
  return (
    <li className={cx("nav-item")}>
      <span onClick={() => setOpen(!open)}>
        <NavItem href="#" title={title} icon={Icon} />
      </span>

      <Collapse in={open}>
        <ul className={cx("nav-content")}>
          {childItems.map((item, index) => (
            <ChildItem key={index} {...item} />
          ))}
        </ul>
      </Collapse>
    </li>
  );
}

export default ExpandNavItem;
