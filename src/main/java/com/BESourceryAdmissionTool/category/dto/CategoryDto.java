package com.BESourceryAdmissionTool.category.dto;
import com.BESourceryAdmissionTool.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDto {
    private List<Category> categories;
}
