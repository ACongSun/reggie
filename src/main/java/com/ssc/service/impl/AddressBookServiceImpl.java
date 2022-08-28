package com.ssc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.entity.AddressBook;
import com.ssc.mapper.AddressBookMapper;
import com.ssc.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @ClassName AddressBookServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/28 20:47:14
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
