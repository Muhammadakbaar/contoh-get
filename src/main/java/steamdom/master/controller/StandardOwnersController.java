package steamdom.master.controller;

import steamdom.master.model.StandardOwners;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.Delete;
import io.micronaut.validation.Validated;
import io.reactivex.annotations.Nullable;

import com.google.gson.Gson;
import steamdom.master.repository.*;

import java.util.HashMap;
import java.util.List;

/* 
* Author : petrik
*/

@Validated
@Controller("/standard_owner")
public class StandardOwnersController {

    private StandardOwnersRepository repository;

    public StandardOwnersController(StandardOwnersRepository repository) {
        this.repository = repository;
    }

    @Get(processes = MediaType.APPLICATION_JSON)
    public String index(@Nullable @QueryValue final int page, @QueryValue final int limit) {
        final HashMap<String, Object> data = new HashMap<>();
        try {
            final List<StandardOwners> standardowners = repository.findAll(page, limit);
            data.put("standardowners", "ok");
            data.put("message", "Data Standard Owners");
            data.put("page", Math.ceil(repository.size() / limit));
            data.put("data", standardowners);
            return (new Gson().toJson(data));
        } catch (Exception e) {
            data.put("standardowners", "error");
            data.put("message", e.getMessage());
            return (new Gson().toJson(data));
        }
    }

    @Get("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String show(@PathVariable @Nullable final Long id) {
        return (new Gson().toJson(repository.findById(id)));
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String save(@Body final StandardOwners standardowners) {
        final HashMap<String, Object> data = new HashMap<>();
        try {
            // boolean exist = repository.existByName(standardowners.getName());
            if (standardowners.getName() == null || standardowners.getName() == "") {
                data.put("standardowners", "error");
                data.put("message", "nama tidak boleh kosong");
                return (new Gson().toJson(data));
            }
            // if(exist == true) {
            StandardOwners result = repository.save(standardowners);
            if (result != null) {
                data.put("standardowners", "ok");
                data.put("message", "Data StandardOwners");
                data.put("data", result);
                return (new Gson().toJson(data));
            } else {
                data.put("standardowners", "error");
                data.put("message", "failed data already exist");
                data.put("data", result);
                return (new Gson().toJson(data));
            }
            // }
            // return (new Gson()).toJson(data);
        } catch (Exception e) {
            String message = e.getMessage();
            data.put("standardowners", "errors");
            data.put("message", message);
            return (new Gson().toJson(data));
        }
    }

    @Put("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathVariable @Nullable final Long id, @Body final StandardOwners standardowners) {
        final HashMap<String, Object> data = new HashMap<>();
        try {
            // boolean exist = repository.existByName(standardowners.getName());
            if (standardowners.getName() == null || standardowners.getName() == "") {
                data.put("standardowners", "error");
                data.put("message", "nama tidak boleh kosong");
                return (new Gson().toJson(data));
            }
            // if(exist == true){
            // data.put("standardowners", "error");
            // data.put("message", "nama tidak boleh sama");
            // return (new Gson().toJson(data));
            // } else {
            StandardOwners result = repository.update(id, standardowners);
            if (result != null) {
                data.put("standardowners", "ok");
                data.put("message", "berhasil memperbarui data");
                data.put("data", result);

                return (new Gson().toJson(data));
            } else {
                data.put("standardowners", "fail");
                data.put("message", "data not found");

                return (new Gson().toJson(data));
            }
            // }
        } catch (Exception e) {
            String message = e.getMessage();
            data.put("standardowners", "error");
            data.put("message", message);
            return (new Gson().toJson(data));
        }
    }

    @Delete("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String destroy(@PathVariable @Nullable final Long id) {
        final HashMap<String, Object> data = new HashMap<>();
        StandardOwners result = repository.destroy(id);
        if (result != null) {
            data.put("standardowners", "ok");
            data.put("message", "berhasil menghapus data");
            data.put("data", result);
        } else {
            data.put("standardowners", "fail");
        }
        return (new Gson().toJson(data));
    }
}