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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Override
    public CompanyEmployeeDto addMemberToCompany(CompanyEmployeeDto companyEmployeeDto) {

        Members members = memberRepository.findById(companyEmployeeDto.getMemberId()).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,
                "Member doesn't exists."));
        Company company1 = companyRepository.findById(companyEmployeeDto.getCompanyId()).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,
                "Company doesn't exists."));
        if(members.getIsWorking()){
            if(companyEmployeeRepository.existsByMemberIdAndCompanyId(companyEmployeeDto.getMemberId(),
                    companyEmployeeDto.getCompanyId())){
                CompanyEmployee companyEmployee = companyEmployeeRepository.findByMemberIdAndCompanyId(companyEmployeeDto.getMemberId(), companyEmployeeDto.getCompanyId());
                if(companyEmployee.getDeletedTimeStamp() == null){
                    throw new AlreadyExistsException(HttpStatus.CONFLICT, "Employee Already exists in company");
                }else{
                    if(companyEmployeeRepository.existsByMemberIdAndDeletedTimeStamp(companyEmployeeDto.getMemberId(),
                            null)){
                        List<CompanyEmployee> companyEmployeeList =companyEmployeeRepository.findByMemberIdAndDeletedTimeStamp(companyEmployeeDto.getMemberId(), null);
                        CompanyEmployee companyEmployee1 = companyEmployeeList.get(0);
                        if(company1.getArea().getCity().equals(companyEmployee1.getCompany().getArea().getCity())){
                            return returnDto(companyEmployeeDto, company1, members);
                        }else{
                            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Employee can't work in two different cities at a time.");
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
                        throw new BadRequestException(HttpStatus.BAD_REQUEST, "Employee can't work in two different cities at a time.");
                    }
                }else{
                    return returnDto(companyEmployeeDto, company1, members);
                }
            }
        }else{
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Member doesn't work.");
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
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessByAreaId(RequestDtoForGettingEmployeesByAreaId dto) {
        Area area = areaRepository.findById(dto.getAreaId()).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "No such area exists with area Id " + dto.getAreaId()));

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryLessThanAndByAreaId(
                dto.getAreaId(),
                dto.getSalary()
        );

        return companyEmployees.stream().map((CompanyEmployee employee)->

                new CompanyEmployeeDto(
                        employee.getCompanyEmployeeId() ,
                        employee.getDesignation(),
                        employee.getSalary())).collect(Collectors.toList());
    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessThanAndByCityId(RequestDtoForGettingEmployeesByCityId dto) {
        City city = cityRepository.findById(dto.getCityId()).orElseThrow(() -> new
                NotFoundException(HttpStatus.NOT_FOUND, "City doesn't exists with cityId = " +dto.getCityId()));

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryLessThanAndByCityId(
                dto.getCityId(),
                dto.getSalary()
        );
        return companyEmployees.stream().map((CompanyEmployee employee)->
                new CompanyEmployeeDto(
                        employee.getCompanyEmployeeId() ,
                        employee.getDesignation(),
                        employee.getSalary())).collect(Collectors.toList());
    }

    @Override
    public List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessThanAndBySocietyId(RequestDtoForGettingEmployeesBySocietyId dto) {
        Society society = societyRepository.findById(dto.getSocietyId()).orElseThrow(()->
                new NotFoundException(HttpStatus.NOT_FOUND, "Society doesn't exists with society Id " +dto.getSocietyId()));

        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.getListOfEmployeesHavingSalaryLessThanAndBySocietyId(
                dto.getSocietyId(),
                dto.getSalary()
        );
        return companyEmployees.stream().map((CompanyEmployee employee)->
                new CompanyEmployeeDto(
                        employee.getCompanyEmployeeId() ,
                        employee.getDesignation(),
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
    public void deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(RequestDtoForEmployeeIdAndCompanyId dto) {
        Members members = memberRepository.findById(dto.getEmployeeId()).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Member doesn't exists."));
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Company doesn't exists."));
        CompanyEmployee companyEmployee = companyEmployeeRepository.findByMemberIdAndCompanyId(
                dto.getEmployeeId(),
                dto.getCompanyId()
        );
        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.findAllByMembersMemberId(dto.getEmployeeId());
        companyEmployees.forEach((e) -> {
            e.setAggregatedSalary(e.getSalary());
            companyEmployeeRepository.save(e);
        });
        companyEmployee.setDeletedTimeStamp(new Date());
        companyEmployeeRepository.save(companyEmployee);
    }

}







