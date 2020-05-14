package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPTS;
  private TorpedoStore mockSTS;
  @BeforeEach
  public void init(){
    mockPTS=mock(TorpedoStore.class);
    mockSTS=mock(TorpedoStore.class);
    this.ship = new GT4500(mockPTS,mockSTS);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    
    when(mockPTS.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPTS,times(1)).fire(1);
    verify(mockSTS,times(0)).fire(1);

  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPTS,times(1)).fire(1);
    verify(mockSTS,times(1)).fire(1);
  }
  @Test
  //Specification based tests
  public void fireTorpedo_AllFailure(){
    when(mockPTS.fire(1)).thenReturn(false);
    when(mockSTS.fire(1)).thenReturn(false);
    boolean result= ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, result);
    verify(mockPTS,times(1)).fire(1);
    verify(mockSTS,times(1)).fire(1);

  }
  @Test
  public void fireTorpedo_SingleFailure(){
    when(mockPTS.fire(1)).thenReturn(false);
    
    boolean result=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(false,result);
    verify(mockPTS,times(1)).fire(1);
    verify(mockSTS,times(0)).fire(1);

  } 
  @Test
  public void fireTorpedo_PrimaryStore_Is_Empty(){
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);
    boolean result=ship.fireTorpedo(FiringMode.SINGLE);
    result=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true,result);
    verify(mockPTS,times(2)).isEmpty();
    verify(mockSTS,times(2)).fire(1);

  }
  @Test
  public void fireTorpedo_Stores_Alternating(){
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);
    boolean result=ship.fireTorpedo(FiringMode.SINGLE);
    result=ship.fireTorpedo(FiringMode.SINGLE);
    result=ship.fireTorpedo(FiringMode.SINGLE);
    result=ship.fireTorpedo(FiringMode.SINGLE);
    
    assertEquals(true,result);
    verify(mockPTS,times(2)).fire(1);
    verify(mockSTS,times(2)).fire(1);
  }
  @Test
  public void fireTorpedo_SecondaryStore_Is_Empty(){
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockSTS.isEmpty()).thenReturn(true);
    
    boolean result=ship.fireTorpedo(FiringMode.SINGLE);
    result=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true,result);
    verify(mockPTS,times(2)).fire(1);
    
    
  }
  @Test
  public void fireTorpedo_Last_Test(){
    when(mockPTS.fire(1)).thenReturn(true);
    boolean result=ship.fireTorpedo(FiringMode.SINGLE);
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.isEmpty()).thenReturn(true);
    result=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(false,result);
    verify(mockPTS,times(1)).fire(1);
    verify(mockPTS,times(2)).isEmpty();
    

  }
  @Test
  public void fireTorpedo_Single_Both_Empty(){
    // Arrange
    
    when(mockSTS.isEmpty()).thenReturn(true);
    
    when(mockPTS.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    assertEquals(false, result);
    verify(mockPTS,times(0)).fire(1);
    verify(mockSTS,times(0)).fire(1);
    
  }
  //Source code based test case
  @Test
  public void fireTorpedo_ALLStores_Empty(){
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.isEmpty()).thenReturn(true);
    boolean result=ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false,result);
    verify(mockPTS,times(1)).isEmpty();
    verify(mockSTS,times(1)).isEmpty();
    verify(mockPTS,times(0)).fire(1);
    verify(mockSTS,times(0)).fire(1);
    
  }
  
}
