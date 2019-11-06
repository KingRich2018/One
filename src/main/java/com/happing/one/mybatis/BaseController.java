package com.happing.one.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.happing.one.domain.enumeration.HttpStatusCoustom;
import com.happing.one.untils.PageConstant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public abstract class BaseController<T>{
    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Autowired
    private BaseMapper<T> baseMapper;

    private static final String DELETE_STATUS_PREFIX = "deleft_status";

    private static final Object DELETE_STATUS_PREFIX_VALUE = true;


    /**
     * 单条记录查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<T> get(@PathVariable Integer id){
        log.debug("get object by id [{}]", id);
        T entity = (T) baseMapper.selectById(id);
        return ResponseEntity.ok(entity);
    }

    /**
     * 新增某条记录
     * @param entity
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity add(@RequestBody T entity){
        log.debug("create object with [{}]", entity);
        Integer b = baseMapper.insert(entity);
        if(b>0){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        log.debug("create object error with [{}]", entity);
        return ResponseEntity.ok(HttpStatusCoustom.EXECUTION_ERROR);
    }



    /**
     * 修改某条记录
     * @param entity
     * @return
     */
    @PutMapping
    @ResponseBody
    public ResponseEntity update(@RequestBody T entity){
        log.debug("update object [{}]", entity);
        Integer b = baseMapper.updateById(entity);
        if(b>0){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.ok(HttpStatusCoustom.EXECUTION_ERROR);
    }

    /**
     * 根据单个Id进行删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id){
        log.debug("delete object by id [{}]", id);
        Integer b = baseMapper.deleteById(id);
        if(b>0){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        log.debug("delete object by id [{}] error", id);
        return ResponseEntity.ok(HttpStatusCoustom.EXECUTION_ERROR);
    }

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    @DeleteMapping
    @ResponseBody
    public ResponseEntity delete(@RequestBody List<Integer> ids){
        log.debug("batch delete object by ids [{}]", ids);
        Integer b = baseMapper.deleteBatchIds(ids);
        if(b>0){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        log.debug("batch delete object by ids [{}] error", ids);
        return ResponseEntity.ok(HttpStatusCoustom.EXECUTION_ERROR);
    }

    /**
     * 查询(非删除状态的)所有数据
     * @return
     */
    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<T>> all(){
        //如果需要排除删除逻辑状态的数据，请根据注释部分重写
        //EntityWrapper<T> wrapper = new EntityWrapper<>();
        // QueryWrapper<T> wrapper = new QueryWrapper<>();
        // wrapper.and(true,query->query.eq("DELETE_STATUS_PREFIX",DELETE_STATUS_PREFIX_VALUE));
        log.debug("get all object with no delete data");
        List<T> list = baseMapper.selectList(null);
        return ResponseEntity.ok(list);
    }

    /**
     * 分页查询，无查询参数，如需要动态查询，则自己重写该方法
     * @return
     */
    @GetMapping
    public ResponseEntity<IPage<T>> getAll(Page page){
        // QueryWrapper<T> wrapper = new QueryWrapper<>();
        // wrapper.and(true,query->query.eq(DELETE_STATUS_PREFIX,DELETE_STATUS_PREFIX_VALUE));
        log.debug("get all object with page size [{}] and page [{}]",page.getSize(),page.getCurrent());
        IPage<T> list = baseMapper.selectPage(buildPage(page),null);
        return ResponseEntity.ok(list);
    }


    /**
     * 获取所有请求参数，封装为map对象
     * @return
     */
    public ResponseEntity<Map<String,Object>> getParameterMap(){
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            parameterMap.put(key,value);
        }
        return ResponseEntity.ok(parameterMap);
    }

    /**
     * 设置分页参数
     * @param <T>
     * @return
     */
    protected <T>Page<T> buildPage(Page page){
        long pageNum = page.getCurrent() < 0 ? PageConstant.PAGE_NUM:page.getCurrent();
        long pageSize = page.getSize() < 0 ? PageConstant.PAGE_SIZE:page.getSize();
        return new Page<>(pageNum,pageSize);
    }

    /**
     * 将HttpServletRequest和response封装到ServletWebRequest中
     * @return
     */
    public ServletWebRequest getServletWebRequest(){
        return new ServletWebRequest(request,response);
    }
}
