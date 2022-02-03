import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/langue">
      <Translate contentKey="global.menu.entities.langue" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/resume">
      <Translate contentKey="global.menu.entities.resume" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/avis">
      <Translate contentKey="global.menu.entities.avis" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/outil">
      <Translate contentKey="global.menu.entities.outil" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adresse">
      <Translate contentKey="global.menu.entities.adresse" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/contact">
      <Translate contentKey="global.menu.entities.contact" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/etude">
      <Translate contentKey="global.menu.entities.etude" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/experience">
      <Translate contentKey="global.menu.entities.experience" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/profil">
      <Translate contentKey="global.menu.entities.profil" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/portfolio">
      <Translate contentKey="global.menu.entities.portfolio" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/programmation">
      <Translate contentKey="global.menu.entities.programmation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/design">
      <Translate contentKey="global.menu.entities.design" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
