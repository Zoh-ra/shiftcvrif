import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './portfolio.reducer';
import { IPortfolio } from 'app/shared/model/portfolio.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Portfolio = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const portfolioList = useAppSelector(state => state.portfolio.entities);
  const loading = useAppSelector(state => state.portfolio.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="portfolio-heading" data-cy="PortfolioHeading">
        <Translate contentKey="cvthequeApp.portfolio.home.title">Portfolios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.portfolio.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.portfolio.home.createLabel">Create new Portfolio</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {portfolioList && portfolioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.portfolio.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.portfolio.image">Image</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.portfolio.lien">Lien</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {portfolioList.map((portfolio, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${portfolio.id}`} color="link" size="sm">
                      {portfolio.id}
                    </Button>
                  </td>
                  <td>{portfolio.image}</td>
                  <td>{portfolio.lien}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${portfolio.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${portfolio.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${portfolio.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cvthequeApp.portfolio.home.notFound">No Portfolios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Portfolio;
