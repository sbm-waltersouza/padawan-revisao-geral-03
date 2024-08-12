import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TreinoListComponent } from './treino-list.component';

describe('TreinoListComponent', () => {
  let component: TreinoListComponent;
  let fixture: ComponentFixture<TreinoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TreinoListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TreinoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
