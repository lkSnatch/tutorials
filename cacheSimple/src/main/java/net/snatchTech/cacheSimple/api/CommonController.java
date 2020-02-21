package net.snatchTech.cacheSimple.api;

import net.snatchTech.cacheSimple.model.CarOwner;
import net.snatchTech.cacheSimple.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/")
@RestController
public class CommonController {

    private final CommonService commonService;

    /*@Autowired
    public CommonController(@Qualifier("CacheWriteBackService") CommonService commonService) {
        this.commonService = commonService;
    }*/

    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @PatchMapping(path = "setOwner")
    public void setOwnerToCar(@RequestParam String number,
                       @RequestParam("name") String fullName) {
        commonService.setOwnerToCar(number, fullName);
    }

    @GetMapping(path = "getOwner")
    public CarOwner getOwnerByCarNumber(@RequestParam String number) {
        return commonService.getOwnerByNumber(number);
    }

}
