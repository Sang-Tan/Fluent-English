import styles from "./../styles.module.scss";
import classnames from "classnames/bind";

const cx = classnames.bind(styles);

function NavItem({ href, title, icon: Icon }) {
  return (
    <li className={cx("nav-item")}>
      <a className={cx("nav-link", "collapsed")} href={href}>
        <Icon />
        <span>{title}</span>
      </a>
    </li>
  );
}

export default NavItem;
