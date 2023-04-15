package com.example.holidayplanner.schedulers;

import com.example.holidayplanner.services.DestinationVisitsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class SchedulerTask {

    private final DestinationVisitsService destinationVisitsService;

    public SchedulerTask(DestinationVisitsService destinationVisitsService) {
        this.destinationVisitsService = destinationVisitsService;
    }

    @Scheduled(cron = "59 59 23 * * *")
    public void reportCurrentTime(){
        File file = new File("C:\\Users\\User\\Desktop\\travel-agency\\src\\main\\resources\\data\\destination-visits.txt");

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write("\n");
            fileWriter.write(LocalDate.now() +":\n");

            destinationVisitsService.findAll()
                    .forEach(destinationVisitsEntity -> {
                        try {
                            fileWriter.write(destinationVisitsEntity.getTravelDestinationEntity().getName()+" - "
                                    +destinationVisitsEntity.getVisits()+"\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
