package steamdom.master.controller;

import steamdom.master.model.StandardYears;
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
@Controller("/standard_years")
public class StandardYearsController {

    private StandardYearsRepository repository;

    public StandardYearsController(StandardYearsRepository repository) {
        this.repository = repository;
    }

    @Get(processes = MediaType.APPLICATION_JSON)
    public String index(@Nullable @QueryValue final int page, @QueryValue final int limit) {
        final HashMap<String, Object> data = new HashMap<>();
        try {
            final List<StandardYears> standardyears = repository.findAll(page, limit);
            data.put("standardyears", "ok");
            data.put("message", "Data Standard Owners");
            data.put("page", Math.ceil(repository.size() / limit));
            data.put("data", standardyears);
            return (new Gson().toJson(data));
        } catch (Exception e) {
            data.put("standardyears", "error");
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
    public String save(@Body final StandardYears standardyears) {
        final HashMap<String, Object> data = new HashMap<>();
        try {
            // boolean exist = repository.existByName(standardyears.getName());
            if (standardyears.getName() == null || standardyears.getName() == "") {
                data.put("standardyears", "error");
                data.put("message", "nama tidak boleh kosong");
                return (new Gson().toJson(data));
            }
            // if(exist == true) {
            StandardYears result = repository.save(standardyears);
            if (result != null) {
                data.put("standardyears", "ok");
                data.put("message", "Data StandardYears");
                data.put("data", result);
                return (new Gson().toJson(data));
            } else {
                data.put("standardyears", "error");
                data.put("message", "failed data already exist");
                data.put("data", result);
                return (new Gson().toJson(data));
            }
            // }
            // return (new Gson()).toJson(data);
        } catch (Exception e) {
            String message = e.getMessage();
            data.put("standardyears", "errors");
            data.put("message", message);
            return (new Gson().toJson(data));
        }
    }

    @Put("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathVariable @Nullable final Long id, @Body final StandardYears standardyears) {
        final HashMap<String, Object> data = new HashMap<>();
        try {
            // boolean exist = repository.existByName(standardyears.getName());
            if (standardyears.getName() == null || standardyears.getName() == "") {
                data.put("standardyears", "error");
                data.put("message", "nama tidak boleh kosong");
                return (new Gson().toJson(data));
            }
            // if(exist == true){
            // data.put("standardyears", "error");
            // data.put("message", "nama tidak boleh sama");
            // return (new Gson().toJson(data));
            // } else {
            StandardYears result = repository.update(id, standardyears);
            if (result != null) {
                data.put("standardyears", "ok");
                data.put("message", "berhasil memperbarui data");
                data.put("data", result);

                return (new Gson().toJson(data));
            } else {
                data.put("standardyears", "fail");
                data.put("message", "data not found");

                return (new Gson().toJson(data));
            }
            // }
        } catch (Exception e) {
            String message = e.getMessage();
            data.put("standardyears", "error");
            data.put("message", message);
            return (new Gson().toJson(data));
        }
    }

    @Delete("{/id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String destroy(@PathVariable @Nullable final Long id) {
        final HashMap<String, Object> data = new HashMap<>();
        StandardYears result = repository.destroy(id);
        if (result != null) {
            data.put("standardyears", "ok");
            data.put("message", "berhasil menghapus data");
            data.put("data", result);
        } else {
            data.put("standardyears", "fail");
        }
        return (new Gson().toJson(data));
    }
}