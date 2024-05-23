package uz.jurayev.dynamic_document_excel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jurayev.dynamic_document_excel.domain.ExcelData;
import uz.jurayev.dynamic_document_excel.enam.ResponseMessage;
import uz.jurayev.dynamic_document_excel.exception.ErrorException;
import uz.jurayev.dynamic_document_excel.repository.ExcelDataRepository;
import uz.jurayev.dynamic_document_excel.util.ExcelUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final ExcelDataRepository excelDataRepository;

    @Transactional
    public String excelToObjects(InputStream is) {

        try {
            List<Map<Object, Object>> maps = ExcelUtil.excelToObject(is);

            List<ExcelData> dataList = new ArrayList<>(10000);
            maps.forEach(elem -> {
                ExcelData data = new ExcelData();
                data.setData(elem);
                dataList.add(data);
            });
            excelDataRepository.saveAll(dataList);
            return ResponseMessage.SUCCESSFULLY.getMessage();
        } catch (RuntimeException e) {
            throw new ErrorException(ResponseMessage.ERROR_CREATED.getMessage());
        }
    }

    public ByteArrayInputStream objectsToExcel(Object[] objects) {

        try {
            return ExcelUtil.objectsToExcel(objects);
        }
        catch (RuntimeException e) {
            throw new ErrorException(ResponseMessage.ERROR.getMessage());
        }
    }
}
