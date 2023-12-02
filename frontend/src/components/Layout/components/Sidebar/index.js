import styles from "./Sidebar.module.scss";
import classnames from "classnames/bind";

import { NavItem, ExpandNavItem } from "./navitem";

import { Grid, Book, Signpost, PencilSquare } from "react-bootstrap-icons";

const cx = classnames.bind(styles);

function Sidebar({ show }) {
  return (
    <aside id="sidebar" className={cx("sidebar", { show })}>
      <ul className={cx("sidebar-nav")} id="sidebar-nav">
        <NavItem href="/admin" title="Dashboard" icon={Grid} />
        <NavItem href="/admin/topic" title="Topics" icon={Book} />
        <NavItem
          href="/admin/learning-path"
          title="Learning paths"
          icon={Signpost}
        />
        <ExpandNavItem
          title="Learning materials"
          icon={PencilSquare}
          childItems={[
            { href: "/admin/learning-material", title: "Learning materials" },
            { href: "/admin/learning-material-category", title: "Categories" },
          ]}
        />
      </ul>
    </aside>
  );
}

export default Sidebar;
