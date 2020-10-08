package cn.sunhomo.controller;

import cn.sunhomo.core.TableDataInfo;
import cn.sunhomo.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseController {
    protected void startPage() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String pageNumber = request.getParameter("pageNumber");
        String pageSize = request.getParameter("pageSize");
        String sortName = request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder");
        if (StringUtils.isNotEmpty(pageNumber) && StringUtils.isNotEmpty(pageSize)) {
            PageHelper.startPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), getOrderBy(sortName, sortOrder));
        }
    }

    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    private String getOrderBy(String orderByColumn, String isAsc) {
        if (StringUtils.isEmpty(orderByColumn)) return "";
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }
}
