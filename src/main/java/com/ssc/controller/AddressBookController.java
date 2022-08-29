package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssc.common.BaseContext;
import com.ssc.common.R;
import com.ssc.entity.AddressBook;
import com.ssc.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AddressBookController
 * @Authoc 孙少聪
 * @Date 2022/8/28 20:48:03
 */


@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;


    /**
     * 获取地址列表
     * @return
     */
    @GetMapping("list")
    public R<List<AddressBook>> list(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        List<AddressBook> list = addressBookService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 获取默认地址
     * @return
     */
    @GetMapping("default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        return R.success(addressBook);
    }

    /**
     * 修改默认地址
     * @param addressBook3
     * @return
     */
    @PutMapping("default")
    public R<String> updateDefault(@RequestBody AddressBook addressBook3){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        addressBook.setIsDefault(0);
        addressBookService.updateById(addressBook);
        LambdaQueryWrapper<AddressBook> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(AddressBook::getId, addressBook3.getId());
        AddressBook addressBook1 = addressBookService.getOne(queryWrapper1);
        addressBook1.setIsDefault(1);
        addressBookService.updateById(addressBook1);
        return R.success("修改成功！！！");
    }

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping()
    public R<String> add(@RequestBody AddressBook addressBook){
        log.info("传过来的数据：{}",addressBook);
        Long currentId = BaseContext.getCurrentId();
        addressBook.setUserId(currentId);
        addressBookService.save(addressBook);
        return R.success("新增成功！！！");
    }

    /**
     * 根据id获取地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getOne(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }


    /**
     * 修改某个地址信息
     * @param addressBook
     * @return
     */
    @PutMapping()
    public R<String> updateOne(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("修改成功！！！");
    }

    /**
     * 删除某个地址
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R<String> deleteOne(@RequestParam Long ids){
        addressBookService.removeById(ids);
        return R.success("删除成功！！");
    }
}
