<template>
	<div class="search-bar">
		<!-- 검색바 -->
		<select v-model="searchParams.orderBy">
			<option
				v-for="(item, idx) in searchCategory"
				:key="idx"
				:value="item"
			>
				{{ item }}
			</option>
		</select>
		<div class="dropdown">
			<button id="instrumentButton" @click="toggleInstrumentDropdown">
				악기
			</button>
			<div
				id="instrumentDropdown"
				class="dropdown-content"
				v-if="showInstrumentDropdown"
			>
				<div class="column">
					<label
						v-for="(value, instrument) in instruments"
						:key="instrument"
					>
						<input
							type="checkbox"
							v-model="instruments[instrument]"
						/>
						{{ instrument }}
					</label>
				</div>
			</div>
		</div>

		<div class="dropdown">
			<button id="careerButton" @click="toggleInputCareer">경력</button>
			<div
				id="careerDropdown"
				class="dropdown-content"
				v-if="showInputCareer"
			>
				<InputCareer
					:career="[searchParams.startCareer, searchParams.endCareer]"
					@careerChange="setCareer"
				/>
			</div>
		</div>

		<div class="dropdown">
			<button id="expenseButton" @click="toggleInputExpense">비용</button>
			<div
				id="expenseDropdown"
				class="dropdown-content"
				v-if="showInputExpense"
			>
				<InputExpense
					:cost="[searchParams.startCost, searchParams.endCost]"
					@costChange="setCost"
				/>
			</div>
		</div>

		<div class="dropdown">
			<button id="timeButton" @click="toggleInputTime">시간</button>
			<div
				id="timeDropdown"
				class="dropdown-content"
				v-if="showInputTime"
			>
				<InputTime
					:days="days"
					:time="[searchParams.startTime, searchParams.endTime]"
					@timeChange="setTime"
					@dayChange="setClassDay"
				/>
			</div>
		</div>

		<input
			placeholder="검색어를 입력하세요."
			type="text"
			v-model="searchParams.keyword"
		/>
		<button @click="searchTeachers">검색</button>
	</div>
</template>

<script>
import InputCareer from "./InputCareer.vue";
import InputExpense from "./InputExpense.vue";
import InputTime from "./InputTime.vue";
import { apiSearchTeacher } from "@/api/search";

export default {
	components: {
		InputCareer,
		InputExpense,
		InputTime,
	},
	data() {
		return {
			showInstrumentDropdown: false,
			showInputCareer: false,
			showInputExpense: false,
			showInputTime: false,

			searchCategory: ["전체", "별점", "매칭"],
			instruments: { 피아노: false, 기타: false, 드럼: false },
			days: {
				월: false,
				화: false,
				수: false,
				목: false,
				금: false,
				토: false,
				일: false,
			},

			searchParams: {
				startCareer: 10,
				endCareer: 90,
				startCost: 0,
				endCost: 10,
				startTime: 0,
				endTime: 23,
				classDay: 0,
				instruments: [],
				keyword: "",
				orderBy: "전체",
				page: 1,
			},
		};
	},
	methods: {
		toggleInstrumentDropdown() {
			this.showInstrumentDropdown = !this.showInstrumentDropdown;
			this.showInputCareer = false;
			this.showInputExpense = false;
			this.showInputTime = false;
		},
		toggleInputCareer() {
			this.showInputCareer = !this.showInputCareer;
			this.showInstrumentDropdown = false;
			this.showInputExpense = false;
			this.showInputTime = false;
		},
		toggleInputExpense() {
			this.showInputExpense = !this.showInputExpense;
			this.showInstrumentDropdown = false;
			this.showInputCareer = false;
			this.showInputTime = false;
		},
		toggleInputTime() {
			this.showInputTime = !this.showInputTime;
			this.showInstrumentDropdown = false;
			this.showInputCareer = false;
			this.showInputExpense = false;
		},

		setCareer(career) {
			console.log("경력 필터 수정", career);
			this.searchParams.startCareer = career[0];
			this.searchParams.endCareer = career[1];
		},

		setCost(cost) {
			console.log("비용 필터 수정", cost);
			this.searchParams.startCost = cost[0];
			this.searchParams.endCost = cost[1];
		},

		setTime(time) {
			console.log("시간 필터 수정", time);
			this.searchParams.startTime = time[0];
			this.searchParams.endTime = time[1];
		},

		setClassDay(value, day) {
			console.log("요일 필터 수정", value, day);
			this.days[day] = value;
		},

		setInstruments() {
			const instrumentsArray = [];
			for (const instrument in this.instruments) {
				if (this.instruments[instrument]) {
					instrumentsArray.push(instrument);
				}
			}
			this.searchParams.instruments = instrumentsArray;
		},

		convertDaysToBitMask() {
			console.log("ConvertDaysToBitMask");
			let index = 0,
				bitMaskedDays = 0;

			for (const day in this.days) {
				if (this.days[day]) {
					bitMaskedDays ^= 1 << index;
				}
				index++;
			}

			console.log(bitMaskedDays.toString(2));
			return bitMaskedDays;
		},

		searchTeachers() {
			this.searchParams.classDay = this.convertDaysToBitMask();
			this.setInstruments();
			console.log(this.searchParams);
			apiSearchTeacher(this.searchParams);
		},
	},
};
</script>

<style scoped>
@import "@/assets/scss/search.scss";

.column {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: left;
}
</style>
