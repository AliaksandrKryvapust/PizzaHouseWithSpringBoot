package groupId.artifactId.manager;

import groupId.artifactId.core.dto.output.CompletedOrderDtoOutput;
import groupId.artifactId.core.dto.output.crud.CompletedOrderDtoCrudOutput;
import groupId.artifactId.core.mapper.CompletedOrderMapper;
import groupId.artifactId.dao.entity.api.ICompletedOrder;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.manager.api.ICompletedOrderManager;
import groupId.artifactId.service.api.ICompletedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompletedOrderManager implements ICompletedOrderManager {
    private final ICompletedOrderService completedOrderService;
    private final CompletedOrderMapper completedOrderMapper;

    @Autowired
    public CompletedOrderManager(ICompletedOrderService completedOrderService, CompletedOrderMapper completedOrderMapper) {
        this.completedOrderService = completedOrderService;
        this.completedOrderMapper = completedOrderMapper;
    }

    @Override
    public CompletedOrderDtoOutput getAllData(Long id) {
        try {
            return completedOrderMapper.outputMapping(this.completedOrderService.get(id));
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to getAllData at Completed order Service by id" + id, e);
        }
    }

    @Override
    public CompletedOrderDtoCrudOutput save(ICompletedOrder type) {
        try {
            ICompletedOrder completedOrder = this.completedOrderService.save(type);
            return this.completedOrderMapper.outputCrudMapping(completedOrder);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        } catch (Exception e) {
            throw new ServiceException("Failed to save Completed order" + type, e);
        }
    }

    @Override
    public List<CompletedOrderDtoCrudOutput> get() {
        try {
            return this.completedOrderService.get().stream()
                    .map(completedOrderMapper::outputCrudMapping).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to get List of Completed order at Service", e);
        }
    }

    @Override
    public CompletedOrderDtoCrudOutput get(Long id) {
        try {
            return completedOrderMapper.outputCrudMapping(this.completedOrderService.get(id));
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to getAllData at Completed order Service by id" + id, e);
        }
    }
}
