import React from 'react';
import { ReactComponent as HeartActivate } from '../assets/Icons/heart/activate.svg';
import { ReactComponent as HeartDefault } from '../assets/Icons/heart/default.svg';
import { ReactComponent as BellOff } from '../assets/Icons/bell/off.svg';
import { ReactComponent as BellOn } from '../assets/Icons/bell/on.svg';
import { ReactComponent as Calendar } from '../assets/Icons/calendar.svg';
import { ReactComponent as Check } from '../assets/Icons/check.svg';
import { ReactComponent as CheckBoxNone } from '../assets/Icons/check_box/noneclick.svg';
import { ReactComponent as CheckBoxClick } from '../assets/Icons/check_box/onclick.svg';
import { ReactComponent as ChevronDown } from '../assets/Icons/chevron_down.svg';
import { ReactComponent as ChevronLeft } from '../assets/Icons/chevron_left.svg';
import { ReactComponent as ChevronRight } from '../assets/Icons/chevron_right.svg';
import { ReactComponent as ChevronUp } from '../assets/Icons/chevron_up.svg';
import { ReactComponent as Crown } from '../assets/Icons/crown.svg';
import { ReactComponent as Delete } from '../assets/Icons/delete.svg';
import { ReactComponent as DeleteSmall } from '../assets/Icons/delete_small.svg';
import { ReactComponent as Dots } from '../assets/Icons/dots.svg';
import { ReactComponent as Edit } from '../assets/Icons/edit.svg';
import { ReactComponent as Eye } from '../assets/Icons/eye.svg';
import { ReactComponent as Hamburger } from '../assets/Icons/hamburger.svg';
import { ReactComponent as Logout } from '../assets/Icons/logout.svg';
import { ReactComponent as Mail } from '../assets/Icons/mail.svg';
import { ReactComponent as People } from '../assets/Icons/people.svg';
import { ReactComponent as PlusM } from '../assets/Icons/plus_m.svg';
import { ReactComponent as PlusS } from '../assets/Icons/plus_s.svg';
import { ReactComponent as Remove } from '../assets/Icons/remove.svg';
import { ReactComponent as SwapVert } from '../assets/Icons/swap_vert.svg';
import { ReactComponent as UnfoldMore } from '../assets/Icons/unfold_more.svg';

const icons = {
  heartFill: HeartActivate,
  heartDefault: HeartDefault,
  bellOff: BellOff,
  bellOn: BellOn,
  calendar: Calendar,
  check: Check,
  checkBoxNone: CheckBoxNone,
  checkBoxClick: CheckBoxClick,
  chevronDown: ChevronDown,
  chevronLeft: ChevronLeft,
  chevronRight: ChevronRight,
  chevronUp: ChevronUp,
  crown: Crown,
  delete: Delete,
  deleteSmall: DeleteSmall,
  dots: Dots,
  edit: Edit,
  eye: Eye,
  hamburger: Hamburger,
  logout: Logout,
  mail: Mail,
  people: People,
  plusM: PlusM,
  plusS: PlusS,
  remove: Remove,
  swapVert: SwapVert,
  unfoldMore: UnfoldMore,
};

const Icon = ({ name, className }) => {
  const IconComponent = icons[name];
  if (!IconComponent) {
    console.error(`Icon "${name}" does not exist.`);
    return null;
  }
  return <IconComponent className={className} />;
};

export default Icon;