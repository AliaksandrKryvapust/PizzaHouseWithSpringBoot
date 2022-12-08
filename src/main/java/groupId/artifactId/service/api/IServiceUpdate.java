package groupId.artifactId.service.api;

public interface IServiceUpdate<TYPE> {
    TYPE update(TYPE type, Long id, Integer version);
}
