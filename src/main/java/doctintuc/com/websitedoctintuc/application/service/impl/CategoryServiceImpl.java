package doctintuc.com.websitedoctintuc.application.service.impl;

import doctintuc.com.websitedoctintuc.application.constants.CommonConstant;
import doctintuc.com.websitedoctintuc.application.constants.DevMessageConstant;
import doctintuc.com.websitedoctintuc.application.jwt.JwtUtils;
import doctintuc.com.websitedoctintuc.application.repository.CategoryRepository;
import doctintuc.com.websitedoctintuc.application.repository.UserRepository;
import doctintuc.com.websitedoctintuc.application.service.ICategoryService;
import doctintuc.com.websitedoctintuc.config.exception.VsException;
import doctintuc.com.websitedoctintuc.domain.dto.CategoryDTO;
import doctintuc.com.websitedoctintuc.domain.entity.Category;
import doctintuc.com.websitedoctintuc.domain.entity.User;
import doctintuc.com.websitedoctintuc.domain.pagine.PaginateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository repository;
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    @Override
    public Category create(CategoryDTO categoryDTO , HttpServletRequest request) {
        if (repository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new VsException(String.format(DevMessageConstant.Common.EXITS_USERNAME, categoryDTO.getCategoryName()));
        }
        String authToken = request.getHeader("Authorization").substring(7);
        String username = jwtUtils.getUserByToken(authToken);
        User user = userRepository.findByUsername(username);
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCreateBy(user.getFullName());
        category.setLastModifiedBy(user.getFullName());
        return repository.save(category);
    }

    @Override
    public Category getCategory(Integer id) {
        if (!repository.existsById(id)) {
            throw new VsException(String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, CommonConstant.ClassName.CATEGORY_CLASS_NAME, id));
        }
        return repository.findById(id).get();
    }

    @Override
    public PaginateDTO<Category> searchPageCategory(Integer page, Integer size) {
        int totalPage = (int) Math.ceil((double) repository.count() / size);
        return new PaginateDTO<>(repository.findAll(PageRequest.of(page, size, Sort.by(CommonConstant.SORT_BY_TIME2)
                .descending())).getContent(), page, totalPage);
    }

    @Override
    public Category update(Integer id, CategoryDTO categoryDTO , HttpServletRequest request) {

        Optional<Category> found_category = repository.findById(id);
        if (found_category.isPresent()) {
            if (categoryDTO.getCategoryName().equals(found_category.get().getCategoryName()) ||
                    !repository.existsByCategoryName(categoryDTO.getCategoryName())) {

                String authToken = request.getHeader("Authorization").substring(7);
                String username = jwtUtils.getUserByToken(authToken);
                User user = userRepository.findByUsername(username);

                Optional<Category> category = Optional.ofNullable(modelMapper.map(categoryDTO, Category.class));
                category.get().setId(id);
                category.get().setCreateBy(found_category.get().getCreateBy());
                category.get().setLastModifiedBy(user.getFullName());
                return repository.save(category.get());
            } else {
                throw new VsException(String.format(DevMessageConstant.Common.EXITS_USERNAME, categoryDTO.getCategoryName()));
            }
        } else {
            throw new VsException(String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, CommonConstant.ClassName.CATEGORY_CLASS_NAME, id));
        }
    }

    @Override
    public String delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new VsException(String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, CommonConstant.ClassName.CATEGORY_CLASS_NAME, id));
        }
        repository.deleteById(id);
        return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
    }

    @Override
    public List<Category> searchAllCategory() {
        return repository.findAll(Sort.by(CommonConstant.SORT_BY_TIME2).ascending());
    }

}
