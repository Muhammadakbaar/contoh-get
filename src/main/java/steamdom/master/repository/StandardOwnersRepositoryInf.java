package steamdom.master.repository;

import steamdom.master.model.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/* 
* Author : petrik
*/

public interface StandardOwnersRepositoryInf {

    List<StandardOwners> findAll(int page, int limit);

    Long size();

    StandardOwners save(@NotNull StandardOwners standardowners);

    StandardOwners update(@NotNull Long id, StandardOwners standardowners);

    StandardOwners findById(@NotNull Long id);

    StandardOwners destroy(@NotNull Long id);

    // boolean existByName(String name);

}