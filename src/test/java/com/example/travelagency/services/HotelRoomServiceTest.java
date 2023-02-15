package com.example.travelagency.services;

import com.example.travelagency.entities.HotelRoomEntity;
import com.example.travelagency.enums.HotelRoomTypeEnum;
import com.example.travelagency.repositories.HotelRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelRoomServiceTest {
    @Mock
    private HotelRoomRepository hotelRoomRepository;

    private HotelRoomService hotelRoomService;

    @BeforeEach
    void setUp(){
        hotelRoomService = new HotelRoomService(hotelRoomRepository);
    }

    @Test
    public void getValidRooms_ShouldReturnValidRoomsByTypeAndHotelId() {
        List<HotelRoomEntity> expectedRooms = new ArrayList<>();
        expectedRooms.add(new HotelRoomEntity());
        when(hotelRoomRepository.findByHotelIdAndType(anyLong(), any(HotelRoomTypeEnum.class))).thenReturn(expectedRooms);

        List<HotelRoomEntity> result = hotelRoomService.getValidRooms(HotelRoomTypeEnum.SINGLE, 1L);

        assertEquals(expectedRooms, result);
        verify(hotelRoomRepository, times(1)).findByHotelIdAndType(anyLong(), any(HotelRoomTypeEnum.class));
    }

    @Test
    public void getValidRooms_ShouldReturnEmptyListWhenNoValidRoomsFound() {
        when(hotelRoomRepository.findByHotelIdAndType(anyLong(), any(HotelRoomTypeEnum.class))).thenReturn(new ArrayList<>());

        List<HotelRoomEntity> result = hotelRoomService.getValidRooms(HotelRoomTypeEnum.SINGLE, 1L);

        assertTrue(result.isEmpty());
        verify(hotelRoomRepository, times(1)).findByHotelIdAndType(anyLong(), any(HotelRoomTypeEnum.class));
    }

}
