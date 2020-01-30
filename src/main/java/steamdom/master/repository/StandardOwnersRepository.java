package steamdom.master.repository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import steamdom.master.model.StandardOwners;

/* 
* Author : petrik
*/

@Singleton
public class StandardOwnersRepository implements StandardOwnersRepositoryInf {
    @PersistenceContext
    private EntityManager entityManager;

    public StandardOwnersRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StandardOwners> findAll(int page, int limit) {
        TypedQuery<StandardOwners> query = entityManager.createQuery("from StandardOwners", StandardOwners.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0).setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional
    @Override
    public StandardOwners save(@NotNull StandardOwners standardowners) {
        entityManager.persist(standardowners);
        return standardowners;
    }

    @Transactional(readOnly = true)
    @Override
    public Long size() {
        return entityManager.createQuery("select count(*) from StandardOwners", Long.class).getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public StandardOwners findById(@NotNull Long id) {
        return entityManager.find(StandardOwners.class, id);
    }

    // @Transactional()
    // @Override
    // public boolean existByName(String name) {
    // return entityManager.find(boolean.class, name);
    // }

    @Transactional
    @Override
    public StandardOwners update(@NotNull Long id, StandardOwners standardowners) {
        String name = standardowners.getName();
        standardowners = entityManager.find(StandardOwners.class, id);
        if (name != null)
            // {
            standardowners.setName(name);
        // }
        // entityManager.merge(standardowners);
        standardowners.setUpdated_at(new Date());
        return standardowners;
    }

    @Transactional
    @Override
    public StandardOwners destroy(@NotNull Long id) {
        StandardOwners standardowners = entityManager.find(StandardOwners.class, id);
        if (standardowners != null) {
            standardowners.setDeleted_at(new Date());
            // entityManager.remove(standardowners);
        }
        return standardowners;
    }
}