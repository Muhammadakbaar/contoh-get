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

import steamdom.master.model.StandardYears;

/* 
* Author : petrik
*/

@Singleton
public class StandardYearsRepository implements StandardYearsRepositoryInf {
    @PersistenceContext
    private EntityManager entityManager;

    public StandardYearsRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StandardYears> findAll(int page, int limit) {
        TypedQuery<StandardYears> query = entityManager.createQuery("from StandardYears", StandardYears.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0).setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional
    @Override
    public StandardYears save(@NotNull StandardYears standardyears) {
        entityManager.persist(standardyears);
        return standardyears;
    }

    @Transactional(readOnly = true)
    @Override
    public Long size() {
        return entityManager.createQuery("select count(*) from StandardYears", Long.class).getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public StandardYears findById(@NotNull Long id) {
        return entityManager.find(StandardYears.class, id);
    }

    // @Transactional()
    // @Override
    // public boolean existByName(String name) {
    // return entityManager.find(boolean.class, name);
    // }

    @Transactional
    @Override
    public StandardYears update(@NotNull Long id, StandardYears standardyears) {
        String name = standardyears.getName();
        standardyears = entityManager.find(StandardYears.class, id);
        if (name != null)
            // {
            standardyears.setName(name);
        // }
        // entityManager.merge(standardyears);
        standardyears.setUpdated_at(new Date());
        return standardyears;
    }

    @Transactional
    @Override
    public StandardYears destroy(@NotNull Long id) {
        StandardYears standardyears = entityManager.find(StandardYears.class, id);
        if (standardyears != null) {
            standardyears.setDeleted_at(new Date());
            // entityManager.remove(standardyears);
        }
        return standardyears;
    }
}