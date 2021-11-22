package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.*;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.*;
import com.example.Demo3.service.CompanyEmployeeService;
import com.example.Demo3.service.MailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyEmployeeServiceImpl implements CompanyEmployeeService {

    private final CompanyEmployeeRepository companyEmployeeRepository;

    private final MemberRepository memberRepository;

    private final CityRepository cityRepository;

    private final AreaRepository areaRepository;

    private final SocietyRepository societyRepository;

    private final CompanyRepository companyRepository;

    private final MailService mailService;

    private final ModelMapper modelMapper = new ModelMapper();

    private final MessageSource messageSource;

    @Override
    public CompanyEmployeeDto addMemberToCompany(CompanyEmployeeDto companyEmployeeDto, Locale locale) {

        Members members = memberRepository.findById(companyEmployeeDto.getMemberId()).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,
                messageSource.getMessage("member_does_not_exists.message", null, locale)));
        Company company1 = companyRepository.findById(companyEmployeeDto.getCompanyId()).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,
                messageSource.getMessage("company_does_not_exists.message", null, locale)));
        if(members.getIsWorking()){
            if(companyEmployeeRepository.existsByMemberIdAndCompanyId(companyEmployeeDto.getMemberId(),
                    companyEmployeeDto.getCompanyId())){
                CompanyEmployee companyEmployee = companyEmployeeRepository.findByMemberIdAndCompanyId(companyEmployeeDto.getMemberId(), companyEmployeeDto.getCompanyId());
                if(companyEmployee.getDeletedTimeStamp() == null){
                    throw new AlreadyExistsException(HttpStatus.CONFLICT, messageSource.getMessage("employee_already_exists_in_company.message", null, locale));
                }else{
                    if(companyEmployeeRepository.existsByMemberIdAndDeletedTimeStamp(companyEmployeeDto.getMemberId(),
                            null)){
                        List<CompanyEmployee> companyEmployeeList =companyEmployeeRepository.findByMemberIdAndDeletedTimeStamp(companyEmployeeDto.getMemberId(), null);
                        CompanyEmployee companyEmployee1 = companyEmployeeList.get(0);
                        if(company1.getArea().getCity().equals(companyEmployee1.getCompany().getArea().getCity())){
                            return returnDto(companyEmployeeDto, company1, members);
                        }else{
                            throw new BadRequestException(HttpStatus.BAD_REQUEST, messageSource.getMessage("employee_cannot_work_in_two_different_cities_at_a_time.message", null, locale));
                        }
                    }else{
                        return returnDto(companyEmployeeDto, company1, members);
                    }
                }
            }else{
                if(companyEmployeeRepository.existsByMemberIdAndDeletedTimeStamp(companyEmployeeDto.getMemberId(),
                        null)){
                    List<CompanyEmployee> companyEmployeeList =companyEmployeeRepository.findByMemberIdAndDeletedTimeStamp(companyEmployeeDto.getMemberId(), null);
                    CompanyEmployee companyEmployee1 = companyEmployeeList.get(0);
//                    CompanyEmployee companyEmployee1= companyEmployeeRepository.findByMemberIdAndDeletedTimeStamp(companyEmployeeDto.getMemberId(), null);
                    if(company1.getArea().getCity().equals(companyEmployee1.getCompany().getArea().getCity())){
                        return returnDto(companyEmployeeDto, company1, members);
                    }else{
                        throw new BadRequestException(HttpStatus.BAD_REQUEST,
                                messageSource.getMessage("employee_cannot_work_in_two_different_cities_at_a_time.message", null, locale));
                    }
                }else{
                    return returnDto(companyEmployeeDto, company1, members);
                }
            }
        }else{
            throw new BadRequestException(HttpStatus.BAD_REQUEST, messageSource.getMessage("member_does_not_work.message", null, locale));
        }
    }

    public CompanyEmployeeDto returnDto(CompanyEmployeeDto companyEmployeeDto, Company company, Members member){
        CompanyEmployee companyEmployee1 = modelMapper.map(companyEmployeeDto, CompanyEmployee.class);
        companyEmployee1.setMembers(member);
        MailDto mail = new MailDto();
        mail.setMailFrom("jiyanikhushali24@gmail.com");
        mail.setMailTo(company.getAdminEmail());
        mail.setMailSubject("Regarding Employees of Company");
        mail.setMailContent("A new employee has been added to your company");
        mailService.sendEmail(mail);
        companyEmployeeRepository.save(companyEmployee1);
        return companyEmployeeDto;
    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterByAreaId(RequestDtoForGettingEmployeesByAreaId dto, Locale locale) {
        Area area = areaRepository.findById(dto.getAreaId()).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("area_does_not_exists.message", null, locale)));

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryGreaterThanAndByAreaId(
                dto.getAreaId(),
                dto.getSalary()
        );

        return companyEmployees.stream().map((CompanyEmployee employee)->

                new CompanyEmployeeDto(
                        employee.getCompanyEmployeeId() ,
                        employee.getSalary())).collect(Collectors.toList());
    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterThanAndByCityId(RequestDtoForGettingEmployeesByCityId dto, Locale locale) {
        City city = cityRepository.findById(dto.getCityId()).orElseThrow(() -> new
                NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("city_does_not_exists.message", null, locale)));

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryGreaterThanAndByCityId(
                dto.getCityId(),
                dto.getSalary()
        );
        return companyEmployees.stream().map((CompanyEmployee employee)->
                new CompanyEmployeeDto(
                        employee.getCompanyEmployeeId() ,
                        employee.getSalary())).collect(Collectors.toList());
    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(RequestDtoForGettingEmployeesBySocietyId dto, Locale locale) {
        Society society = societyRepository.findById(dto.getSocietyId()).orElseThrow(()->
                new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("society_does_not_exists.message", null, locale)));

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(
                dto.getSocietyId(),
                dto.getSalary()
        );
        return companyEmployees.stream().map((CompanyEmployee employee)->
                new CompanyEmployeeDto(
                        employee.getCompanyEmployeeId() ,
                        employee.getSalary())).collect(Collectors.toList());
    }

    @Override
    public Page<CompanyEmployeeDto> getEmployeeListByKeywordAndCompanyId(RequestDtoToGetEmployeeListByKeywordAndCompanyId dto) {
        int pageSize = 5;
        int pageNo = dto.getPageNo();
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.findEmployeeByKeyword(dto.getCompanyId(),dto.getKeyword());
        List<CompanyEmployeeDto> companyEmployeeList = companyEmployees.stream().map((CompanyEmployee c) ->
                new CompanyEmployeeDto(
                        c.getCompanyEmployeeId(),
                        c.getDesignation())).collect(Collectors.toList());
        return new PageImpl<>(companyEmployeeList,  pageable, companyEmployeeList.size());
    }

    @Override
    public void deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(RequestDtoForEmployeeIdAndCompanyId dto, Locale locale) {
        Members members = memberRepository.findById(dto.getEmployeeId()).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                messageSource.getMessage("member_does_not_exists.message", null, locale)));
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                messageSource.getMessage("company_does_not_exists.message", null, locale)));
        CompanyEmployee companyEmployee = companyEmployeeRepository.findByMemberIdAndCompanyId(
                dto.getEmployeeId(),
                dto.getCompanyId()
        );
        companyEmployee.setDeletedTimeStamp(new Date());
        companyEmployeeRepository.save(companyEmployee);
    }

}







