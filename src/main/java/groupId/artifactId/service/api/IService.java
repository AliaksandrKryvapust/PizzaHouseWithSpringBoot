package groupId.artifactId.service.api;

import java.util.List;

public interface IService<TYPE> {
    TYPE save(TYPE type);
    List<TYPE> get();
    TYPE get(Long id);
}
