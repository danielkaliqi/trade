import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyItemsComponent } from './myitems.component';

describe('MyItemsComponent', () => {
  let component: MyItemsComponent;
  let fixture: ComponentFixture<MyItemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyItemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
