package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.City;
import com.example.Demo3.entities.Society;
import com.example.Demo3.entities.User;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.AreaRepository;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.repository.SocietyRepository;
import com.example.Demo3.repository.UserRepository;
import com.example.Demo3.service.MailService;
import com.example.Demo3.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocietyServiceImpl implements SocietyService {

    private final MailService mailService;

    private final AreaRepository areaRepository;

    private final UserRepository userRepository;

    private final SocietyRepository societyRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    private final MessageSource messageSource;

    @Override
    public SocietyDto addSociety(SocietyDto societyDto, Locale locale) {
        Area area = areaRepository.findById(societyDto.getAreaId()).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("area_does_not_exists.message",
                        null,
                        locale)));
        if (!userRepository.existsByUserEmail(societyDto.getUserDto().getUserEmail())) {
            Society society = modelMapper.map(societyDto, Society.class);
            UserDto userDto = societyDto.getUserDto();
            User user = modelMapper.map(userDto, User.class);
            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
            userRepository.save(user);
            society.setSocietyAdminEmail(user.getUserEmail());
            societyRepository.save(society);
            User u = userRepository.findByUserRole("Admin");
            MailDto mail = new MailDto();
            mail.setMailFrom("jiyanikhushali24@gmail.com");
            mail.setMailTo(u.getUserEmail());
            mail.setMailSubject("Regarding Admin of Society");
            mail.setMailContent("You are the Society Admin of " +societyDto.getSocietyName());
            mailService.sendEmail(mail);
            societyDto.getUserDto().setUserPassword(null);
            return societyDto;
        } else {
            throw new AlreadyExistsException(HttpStatus.CONFLICT, messageSource.getMessage("society_admin_already_exists.message",
                    null,
                    locale));
        }
    }

    @Override
    public Page<SocietyDto> getAllSociety(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Society> societies = societyRepository.findAll(pageable);
        List<SocietyDto> societyDtoList = societies.stream().map((Society society) ->
                new SocietyDto(
                        society.getSocietyId(),
                        society.getSocietyName())).collect(Collectors.toList());
        return new PageImpl<>(societyDtoList,  pageable, societyDtoList.size());
    }

    @Override
    public SocietyDto getSocietyBySocietyId(Long societyId, Locale locale) {
        Society society = societyRepository.findById(societyId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                messageSource.getMessage("society_does_not_exists.message",
                        null,
                        locale)));
        SocietyDto societyDto = modelMapper.map(society,SocietyDto.class);
        AreaDto areaDto = modelMapper.map(society.getArea(), AreaDto.class);
        CityDto cityDto = modelMapper.map(society.getArea().getCity(), CityDto.class);
        cityDto.setCityState(null);
        areaDto.setCityDto(cityDto);
        areaDto.setCityId(null);
        societyDto.setAreaDto(areaDto);
        societyDto.setSocietyAddress(null);
        societyDto.setAreaId(null);
        return societyDto;
    }

    @Override
    public List<SocietyDto> getSocietyByAreaId(Long areaId, Locale locale) {
        if(areaId != null){
            Area area = areaRepository.findById(areaId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("area_does_not_exists.message",
                            null,
                            locale)));
            List<Society> societyList = societyRepository.findAllByAreaAreaId(areaId);
            return societyList.stream().map((Society society) ->
                    new SocietyDto(
                            society.getSocietyId(),
                            society.getSocietyName())).collect(Collectors.toList());
        }
       else {
           throw new BadRequestException(HttpStatus.BAD_REQUEST, messageSource.getMessage("area_id_cannot_be_null",
                   null,
                   locale));
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
