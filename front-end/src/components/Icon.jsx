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

import { ReactComponent as Bear } from '../assets/Icons/animals/bear.svg';
import { ReactComponent as DogFace } from '../assets/Icons/animals/dog_face.svg';
import { ReactComponent as Fish } from '../assets/Icons/animals/fish.svg';
import { ReactComponent as Fox } from '../assets/Icons/animals/fox.svg';
import { ReactComponent as Frog } from '../assets/Icons/animals/frog.svg';
import { ReactComponent as Hamster } from '../assets/Icons/animals/hamster.svg';
import { ReactComponent as Koala } from '../assets/Icons/animals/koala.svg';
import { ReactComponent as LadyBeetle } from '../assets/Icons/animals/lady_beetle.svg';
import { ReactComponent as Lion } from '../assets/Icons/animals/lion.svg';
import { ReactComponent as MonkeyFace } from '../assets/Icons/animals/monkey_face.svg';
import { ReactComponent as MouseFace } from '../assets/Icons/animals/mouse_face.svg';
import { ReactComponent as Octopus } from '../assets/Icons/animals/octopus.svg';
import { ReactComponent as Orangutan } from '../assets/Icons/animals/orangutan.svg';
import { ReactComponent as Panda } from '../assets/Icons/animals/panda.svg';
import { ReactComponent as Parrot } from '../assets/Icons/animals/parrot.svg';
import { ReactComponent as Penguin } from '../assets/Icons/animals/penguin.svg';
import { ReactComponent as PigFace } from '../assets/Icons/animals/pig_face.svg';
import { ReactComponent as PolarBear } from '../assets/Icons/animals/polar_bear.svg';
import { ReactComponent as RabbitFace } from '../assets/Icons/animals/rabbit_face.svg';
import { ReactComponent as Rooster } from '../assets/Icons/animals/rooster.svg';
import { ReactComponent as Shark } from '../assets/Icons/animals/shark.svg';
import { ReactComponent as Skunk } from '../assets/Icons/animals/skunk.svg';
import { ReactComponent as SpoutingWhale } from '../assets/Icons/animals/spouting_whale.svg';
import { ReactComponent as Swan } from '../assets/Icons/animals/swan.svg';
import { ReactComponent as Turtle } from '../assets/Icons/animals/turtle.svg';
import { ReactComponent as Whale } from '../assets/Icons/animals/whale.svg';
import { ReactComponent as Wolf } from '../assets/Icons/animals/wolf.svg';

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

  '@animals/bear': Bear,
  '@animals/dog_face': DogFace,
  '@animals/fish': Fish,
  '@animals/fox': Fox,
  '@animals/frog': Frog,
  '@animals/hamster': Hamster,
  '@animals/koala': Koala,
  '@animals/lady_beetle': LadyBeetle,
  '@animals/lion': Lion,
  '@animals/monkey_face': MonkeyFace,
  '@animals/mouse_face': MouseFace,
  '@animals/octopus': Octopus,
  '@animals/orangutan': Orangutan,
  '@animals/panda': Panda,
  '@animals/parrot': Parrot,
  '@animals/penguin': Penguin,
  '@animals/pig_face': PigFace,
  '@animals/polar_bear': PolarBear,
  '@animals/rabbit_face': RabbitFace,
  '@animals/rooster': Rooster,
  '@animals/shark': Shark,
  '@animals/skunk': Skunk,
  '@animals/spouting_whale': SpoutingWhale,
  '@animals/swan': Swan,
  '@animals/turtle': Turtle,
  '@animals/whale': Whale,
  '@animals/wolf': Wolf,
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
